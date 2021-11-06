jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INBPalette, NBPalette } from '../nb-palette.model';
import { NBPaletteService } from '../service/nb-palette.service';

import { NBPaletteRoutingResolveService } from './nb-palette-routing-resolve.service';

describe('NBPalette routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NBPaletteRoutingResolveService;
  let service: NBPaletteService;
  let resultNBPalette: INBPalette | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NBPaletteRoutingResolveService);
    service = TestBed.inject(NBPaletteService);
    resultNBPalette = undefined;
  });

  describe('resolve', () => {
    it('should return INBPalette returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBPalette = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBPalette).toEqual({ id: 123 });
    });

    it('should return new INBPalette if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBPalette = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNBPalette).toEqual(new NBPalette());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NBPalette })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBPalette = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBPalette).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
