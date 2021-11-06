import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INBMap, NBMap } from '../nb-map.model';
import { NBMapService } from '../service/nb-map.service';

@Injectable({ providedIn: 'root' })
export class NBMapRoutingResolveService implements Resolve<INBMap> {
  constructor(protected service: NBMapService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INBMap> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nBMap: HttpResponse<NBMap>) => {
          if (nBMap.body) {
            return of(nBMap.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NBMap());
  }
}
