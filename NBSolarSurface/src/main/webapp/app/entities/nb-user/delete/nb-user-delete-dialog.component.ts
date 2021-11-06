import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INBUser } from '../nb-user.model';
import { NBUserService } from '../service/nb-user.service';

@Component({
  templateUrl: './nb-user-delete-dialog.component.html',
})
export class NBUserDeleteDialogComponent {
  nBUser?: INBUser;

  constructor(protected nBUserService: NBUserService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nBUserService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
