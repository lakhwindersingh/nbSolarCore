import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INBChart, NBChart } from '../nb-chart.model';
import { NBChartService } from '../service/nb-chart.service';

@Component({
  selector: 'jhi-nb-chart-update',
  templateUrl: './nb-chart-update.component.html',
})
export class NBChartUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nbChartID: [],
    nbChartTitle: [],
    nbChartType: [],
    nbChartParams: [],
    nbLastUpdated: [],
    nbLastUpdatedBy: [],
  });

  constructor(protected nBChartService: NBChartService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBChart }) => {
      this.updateForm(nBChart);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nBChart = this.createFromForm();
    if (nBChart.id !== undefined) {
      this.subscribeToSaveResponse(this.nBChartService.update(nBChart));
    } else {
      this.subscribeToSaveResponse(this.nBChartService.create(nBChart));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INBChart>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(nBChart: INBChart): void {
    this.editForm.patchValue({
      id: nBChart.id,
      nbChartID: nBChart.nbChartID,
      nbChartTitle: nBChart.nbChartTitle,
      nbChartType: nBChart.nbChartType,
      nbChartParams: nBChart.nbChartParams,
      nbLastUpdated: nBChart.nbLastUpdated,
      nbLastUpdatedBy: nBChart.nbLastUpdatedBy,
    });
  }

  protected createFromForm(): INBChart {
    return {
      ...new NBChart(),
      id: this.editForm.get(['id'])!.value,
      nbChartID: this.editForm.get(['nbChartID'])!.value,
      nbChartTitle: this.editForm.get(['nbChartTitle'])!.value,
      nbChartType: this.editForm.get(['nbChartType'])!.value,
      nbChartParams: this.editForm.get(['nbChartParams'])!.value,
      nbLastUpdated: this.editForm.get(['nbLastUpdated'])!.value,
      nbLastUpdatedBy: this.editForm.get(['nbLastUpdatedBy'])!.value,
    };
  }
}
