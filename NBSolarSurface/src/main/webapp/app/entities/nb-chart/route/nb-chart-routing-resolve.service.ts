import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INBChart, NBChart } from '../nb-chart.model';
import { NBChartService } from '../service/nb-chart.service';

@Injectable({ providedIn: 'root' })
export class NBChartRoutingResolveService implements Resolve<INBChart> {
  constructor(protected service: NBChartService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INBChart> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nBChart: HttpResponse<NBChart>) => {
          if (nBChart.body) {
            return of(nBChart.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NBChart());
  }
}
