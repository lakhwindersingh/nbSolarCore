import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INBPalette, NBPalette } from '../nb-palette.model';
import { NBPaletteService } from '../service/nb-palette.service';

@Component({
  selector: 'jhi-nb-palette-update',
  templateUrl: './nb-palette-update.component.html',
})
export class NBPaletteUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nbPaletteID: [],
    nbPaletteTitle: [],
    nbPaletteType: [],
    nbPaletteColors: [],
    nbLastUpdated: [],
    nbLastUpdatedBy: [],
  });

  constructor(protected nBPaletteService: NBPaletteService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBPalette }) => {
      this.updateForm(nBPalette);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nBPalette = this.createFromForm();
    if (nBPalette.id !== undefined) {
      this.subscribeToSaveResponse(this.nBPaletteService.update(nBPalette));
    } else {
      this.subscribeToSaveResponse(this.nBPaletteService.create(nBPalette));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INBPalette>>): void {
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

  protected updateForm(nBPalette: INBPalette): void {
    this.editForm.patchValue({
      id: nBPalette.id,
      nbPaletteID: nBPalette.nbPaletteID,
      nbPaletteTitle: nBPalette.nbPaletteTitle,
      nbPaletteType: nBPalette.nbPaletteType,
      nbPaletteColors: nBPalette.nbPaletteColors,
      nbLastUpdated: nBPalette.nbLastUpdated,
      nbLastUpdatedBy: nBPalette.nbLastUpdatedBy,
    });
  }

  protected createFromForm(): INBPalette {
    return {
      ...new NBPalette(),
      id: this.editForm.get(['id'])!.value,
      nbPaletteID: this.editForm.get(['nbPaletteID'])!.value,
      nbPaletteTitle: this.editForm.get(['nbPaletteTitle'])!.value,
      nbPaletteType: this.editForm.get(['nbPaletteType'])!.value,
      nbPaletteColors: this.editForm.get(['nbPaletteColors'])!.value,
      nbLastUpdated: this.editForm.get(['nbLastUpdated'])!.value,
      nbLastUpdatedBy: this.editForm.get(['nbLastUpdatedBy'])!.value,
    };
  }
}
