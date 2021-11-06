import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NBChartComponent } from '../list/nb-chart.component';
import { NBChartDetailComponent } from '../detail/nb-chart-detail.component';
import { NBChartUpdateComponent } from '../update/nb-chart-update.component';
import { NBChartRoutingResolveService } from './nb-chart-routing-resolve.service';

const nBChartRoute: Routes = [
  {
    path: '',
    component: NBChartComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NBChartDetailComponent,
    resolve: {
      nBChart: NBChartRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NBChartUpdateComponent,
    resolve: {
      nBChart: NBChartRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NBChartUpdateComponent,
    resolve: {
      nBChart: NBChartRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nBChartRoute)],
  exports: [RouterModule],
})
export class NBChartRoutingModule {}
