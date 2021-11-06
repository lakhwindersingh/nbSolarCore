import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'nb-map',
        data: { pageTitle: 'nbSolarSurfaceApp.nBMap.home.title' },
        loadChildren: () => import('./nb-map/nb-map.module').then(m => m.NBMapModule),
      },
      {
        path: 'nb-chart',
        data: { pageTitle: 'nbSolarSurfaceApp.nBChart.home.title' },
        loadChildren: () => import('./nb-chart/nb-chart.module').then(m => m.NBChartModule),
      },
      {
        path: 'nb-user',
        data: { pageTitle: 'nbSolarSurfaceApp.nBUser.home.title' },
        loadChildren: () => import('./nb-user/nb-user.module').then(m => m.NBUserModule),
      },
      {
        path: 'nb-palette',
        data: { pageTitle: 'nbSolarSurfaceApp.nBPalette.home.title' },
        loadChildren: () => import('./nb-palette/nb-palette.module').then(m => m.NBPaletteModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
