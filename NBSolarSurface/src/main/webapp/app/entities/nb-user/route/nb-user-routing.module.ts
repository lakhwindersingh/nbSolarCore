import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NBUserComponent } from '../list/nb-user.component';
import { NBUserDetailComponent } from '../detail/nb-user-detail.component';
import { NBUserUpdateComponent } from '../update/nb-user-update.component';
import { NBUserRoutingResolveService } from './nb-user-routing-resolve.service';

const nBUserRoute: Routes = [
  {
    path: '',
    component: NBUserComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NBUserDetailComponent,
    resolve: {
      nBUser: NBUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NBUserUpdateComponent,
    resolve: {
      nBUser: NBUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NBUserUpdateComponent,
    resolve: {
      nBUser: NBUserRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nBUserRoute)],
  exports: [RouterModule],
})
export class NBUserRoutingModule {}
