import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INBPalette } from '../nb-palette.model';
import { NBPaletteService } from '../service/nb-palette.service';

@Component({
  templateUrl: './nb-palette-delete-dialog.component.html',
})
export class NBPaletteDeleteDialogComponent {
  nBPalette?: INBPalette;

  constructor(protected nBPaletteService: NBPaletteService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nBPaletteService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
