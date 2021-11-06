import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NBChartComponent } from './list/nb-chart.component';
import { NBChartDetailComponent } from './detail/nb-chart-detail.component';
import { NBChartUpdateComponent } from './update/nb-chart-update.component';
import { NBChartDeleteDialogComponent } from './delete/nb-chart-delete-dialog.component';
import { NBChartRoutingModule } from './route/nb-chart-routing.module';

@NgModule({
  imports: [SharedModule, NBChartRoutingModule],
  declarations: [NBChartComponent, NBChartDetailComponent, NBChartUpdateComponent, NBChartDeleteDialogComponent],
  entryComponents: [NBChartDeleteDialogComponent],
})
export class NBChartModule {}
