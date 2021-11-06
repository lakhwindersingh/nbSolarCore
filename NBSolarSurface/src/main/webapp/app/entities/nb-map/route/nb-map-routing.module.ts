import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NBMapComponent } from '../list/nb-map.component';
import { NBMapDetailComponent } from '../detail/nb-map-detail.component';
import { NBMapUpdateComponent } from '../update/nb-map-update.component';
import { NBMapRoutingResolveService } from './nb-map-routing-resolve.service';

const nBMapRoute: Routes = [
  {
    path: '',
    component: NBMapComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NBMapDetailComponent,
    resolve: {
      nBMap: NBMapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NBMapUpdateComponent,
    resolve: {
      nBMap: NBMapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NBMapUpdateComponent,
    resolve: {
      nBMap: NBMapRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nBMapRoute)],
  exports: [RouterModule],
})
export class NBMapRoutingModule {}
