import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NBPaletteComponent } from '../list/nb-palette.component';
import { NBPaletteDetailComponent } from '../detail/nb-palette-detail.component';
import { NBPaletteUpdateComponent } from '../update/nb-palette-update.component';
import { NBPaletteRoutingResolveService } from './nb-palette-routing-resolve.service';

const nBPaletteRoute: Routes = [
  {
    path: '',
    component: NBPaletteComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NBPaletteDetailComponent,
    resolve: {
      nBPalette: NBPaletteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NBPaletteUpdateComponent,
    resolve: {
      nBPalette: NBPaletteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NBPaletteUpdateComponent,
    resolve: {
      nBPalette: NBPaletteRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nBPaletteRoute)],
  exports: [RouterModule],
})
export class NBPaletteRoutingModule {}
