import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NBUserComponent } from './list/nb-user.component';
import { NBUserDetailComponent } from './detail/nb-user-detail.component';
import { NBUserUpdateComponent } from './update/nb-user-update.component';
import { NBUserDeleteDialogComponent } from './delete/nb-user-delete-dialog.component';
import { NBUserRoutingModule } from './route/nb-user-routing.module';

@NgModule({
  imports: [SharedModule, NBUserRoutingModule],
  declarations: [NBUserComponent, NBUserDetailComponent, NBUserUpdateComponent, NBUserDeleteDialogComponent],
  entryComponents: [NBUserDeleteDialogComponent],
})
export class NBUserModule {}
