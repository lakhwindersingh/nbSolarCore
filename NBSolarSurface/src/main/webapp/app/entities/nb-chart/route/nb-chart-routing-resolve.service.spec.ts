jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { INBChart, NBChart } from '../nb-chart.model';
import { NBChartService } from '../service/nb-chart.service';

import { NBChartRoutingResolveService } from './nb-chart-routing-resolve.service';

describe('NBChart routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: NBChartRoutingResolveService;
  let service: NBChartService;
  let resultNBChart: INBChart | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(NBChartRoutingResolveService);
    service = TestBed.inject(NBChartService);
    resultNBChart = undefined;
  });

  describe('resolve', () => {
    it('should return INBChart returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBChart = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBChart).toEqual({ id: 123 });
    });

    it('should return new INBChart if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBChart = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultNBChart).toEqual(new NBChart());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as NBChart })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultNBChart = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultNBChart).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
