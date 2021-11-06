import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INBMap } from '../nb-map.model';
import { NBMapService } from '../service/nb-map.service';

@Component({
  templateUrl: './nb-map-delete-dialog.component.html',
})
export class NBMapDeleteDialogComponent {
  nBMap?: INBMap;

  constructor(protected nBMapService: NBMapService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nBMapService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
