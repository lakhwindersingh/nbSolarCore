jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INBUser, NBUser } from '../nb-user.model';
import { NBUserService } from '../service/nb-user.service';

import { NBUserRoutingResolveService } from './nb-user-routing-resolve.service';

describe('NBUser routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NBUserRoutingResolveService;
  let service: NBUserService;
  let resultNBUser: INBUser | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NBUserRoutingResolveService);
    service = TestBed.inject(NBUserService);
    resultNBUser = undefined;
  });

  describe('resolve', () => {
    it('should return INBUser returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBUser = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBUser).toEqual({ id: 123 });
    });

    it('should return new INBUser if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBUser = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNBUser).toEqual(new NBUser());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NBUser })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBUser = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBUser).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
