jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INBMap, NBMap } from '../nb-map.model';
import { NBMapService } from '../service/nb-map.service';

import { NBMapRoutingResolveService } from './nb-map-routing-resolve.service';

describe('NBMap routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NBMapRoutingResolveService;
  let service: NBMapService;
  let resultNBMap: INBMap | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NBMapRoutingResolveService);
    service = TestBed.inject(NBMapService);
    resultNBMap = undefined;
  });

  describe('resolve', () => {
    it('should return INBMap returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBMap = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBMap).toEqual({ id: 123 });
    });

    it('should return new INBMap if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBMap = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNBMap).toEqual(new NBMap());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NBMap })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBMap = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBMap).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
