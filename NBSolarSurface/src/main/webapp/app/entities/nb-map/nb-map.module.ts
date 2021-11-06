import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NBMapComponent } from './list/nb-map.component';
import { NBMapDetailComponent } from './detail/nb-map-detail.component';
import { NBMapUpdateComponent } from './update/nb-map-update.component';
import { NBMapDeleteDialogComponent } from './delete/nb-map-delete-dialog.component';
import { NBMapRoutingModule } from './route/nb-map-routing.module';

@NgModule({
  imports: [SharedModule, NBMapRoutingModule],
  declarations: [NBMapComponent, NBMapDetailComponent, NBMapUpdateComponent, NBMapDeleteDialogComponent],
  entryComponents: [NBMapDeleteDialogComponent],
})
export class NBMapModule {}
