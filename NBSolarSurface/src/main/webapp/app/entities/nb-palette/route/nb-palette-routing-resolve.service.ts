import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { INBPalette, NBPalette } from '../nb-palette.model';
import { NBPaletteService } from '../service/nb-palette.service';

@Injectable({ providedIn: 'root' })
export class NBPaletteRoutingResolveService implements Resolve<INBPalette> {
  constructor(protected service: NBPaletteService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INBPalette> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((nBPalette: HttpResponse<NBPalette>) => {
          if (nBPalette.body) {
            return of(nBPalette.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NBPalette());
  }
}
