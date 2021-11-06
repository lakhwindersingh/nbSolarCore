import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NBPaletteComponent } from './list/nb-palette.component';
import { NBPaletteDetailComponent } from './detail/nb-palette-detail.component';
import { NBPaletteUpdateComponent } from './update/nb-palette-update.component';
import { NBPaletteDeleteDialogComponent } from './delete/nb-palette-delete-dialog.component';
import { NBPaletteRoutingModule } from './route/nb-palette-routing.module';

@NgModule({
  imports: [SharedModule, NBPaletteRoutingModule],
  declarations: [NBPaletteComponent, NBPaletteDetailComponent, NBPaletteUpdateComponent, NBPaletteDeleteDialogComponent],
  entryComponents: [NBPaletteDeleteDialogComponent],
})
export class NBPaletteModule {}
