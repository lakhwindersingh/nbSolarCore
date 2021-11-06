import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INBChart } from '../nb-chart.model';

@Component({
  selector: 'jhi-nb-chart-detail',
  templateUrl: './nb-chart-detail.component.html',
})
export class NBChartDetailComponent implements OnInit {
  nBChart: INBChart | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBChart }) => {
      this.nBChart = nBChart;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
