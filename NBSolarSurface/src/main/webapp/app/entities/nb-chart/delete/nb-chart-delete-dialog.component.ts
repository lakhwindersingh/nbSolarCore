import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { INBChart } from '../nb-chart.model';
import { NBChartService } from '../service/nb-chart.service';

@Component({
  templateUrl: './nb-chart-delete-dialog.component.html',
})
export class NBChartDeleteDialogComponent {
  nBChart?: INBChart;

  constructor(protected nBChartService: NBChartService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nBChartService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
