import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INBUser, NBUser } from '../nb-user.model';
import { NBUserService } from '../service/nb-user.service';

@Injectable({ providedIn: 'root' })
export class NBUserRoutingResolveService implements Resolve<INBUser> {
  constructor(protected service: NBUserService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INBUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nBUser: HttpResponse<NBUser>) => {
          if (nBUser.body) {
            return of(nBUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NBUser());
  }
}
