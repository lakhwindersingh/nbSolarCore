import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INBChart } from '../nb-chart.model';
import { NBChartService } from '../service/nb-chart.service';
import { NBChartDeleteDialogComponent } from '../delete/nb-chart-delete-dialog.component';

@Component({
  selector: 'jhi-nb-chart',
  templateUrl: './nb-chart.component.html',
})
export class NBChartComponent implements OnInit {
  nBCharts?: INBChart[];
  isLoading = false;

  constructor(protected nBChartService: NBChartService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nBChartService.query().subscribe(
      (res: HttpResponse<INBChart[]>) => {
        this.isLoading = false;
        this.nBCharts = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INBChart): number {
    return item.id!;
  }

  delete(nBChart: INBChart): void {
    const modalRef = this.modalService.open(NBChartDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nBChart = nBChart;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
