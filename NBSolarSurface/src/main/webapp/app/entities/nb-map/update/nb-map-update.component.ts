import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INBMap, NBMap } from '../nb-map.model';
import { NBMapService } from '../service/nb-map.service';

@Component({
  selector: 'jhi-nb-map-update',
  templateUrl: './nb-map-update.component.html',
})
export class NBMapUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nbID: [],
    nbName: [],
    nbOwner: [],
    nbOwnerPrivateKey: [],
    nbOwnerPublicKey: [],
    nbMapPublishMethod: [],
    nbSubscriptionDate: [],
    nbSubscriptionLastDate: [],
    nbLastUpdated: [],
    nbLastUpdatedBy: [],
  });

  constructor(protected nBMapService: NBMapService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBMap }) => {
      this.updateForm(nBMap);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nBMap = this.createFromForm();
    if (nBMap.id !== undefined) {
      this.subscribeToSaveResponse(this.nBMapService.update(nBMap));
    } else {
      this.subscribeToSaveResponse(this.nBMapService.create(nBMap));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INBMap>>): void {
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

  protected updateForm(nBMap: INBMap): void {
    this.editForm.patchValue({
      id: nBMap.id,
      nbID: nBMap.nbID,
      nbName: nBMap.nbName,
      nbOwner: nBMap.nbOwner,
      nbOwnerPrivateKey: nBMap.nbOwnerPrivateKey,
      nbOwnerPublicKey: nBMap.nbOwnerPublicKey,
      nbMapPublishMethod: nBMap.nbMapPublishMethod,
      nbSubscriptionDate: nBMap.nbSubscriptionDate,
      nbSubscriptionLastDate: nBMap.nbSubscriptionLastDate,
      nbLastUpdated: nBMap.nbLastUpdated,
      nbLastUpdatedBy: nBMap.nbLastUpdatedBy,
    });
  }

  protected createFromForm(): INBMap {
    return {
      ...new NBMap(),
      id: this.editForm.get(['id'])!.value,
      nbID: this.editForm.get(['nbID'])!.value,
      nbName: this.editForm.get(['nbName'])!.value,
      nbOwner: this.editForm.get(['nbOwner'])!.value,
      nbOwnerPrivateKey: this.editForm.get(['nbOwnerPrivateKey'])!.value,
      nbOwnerPublicKey: this.editForm.get(['nbOwnerPublicKey'])!.value,
      nbMapPublishMethod: this.editForm.get(['nbMapPublishMethod'])!.value,
      nbSubscriptionDate: this.editForm.get(['nbSubscriptionDate'])!.value,
      nbSubscriptionLastDate: this.editForm.get(['nbSubscriptionLastDate'])!.value,
      nbLastUpdated: this.editForm.get(['nbLastUpdated'])!.value,
      nbLastUpdatedBy: this.editForm.get(['nbLastUpdatedBy'])!.value,
    };
  }
}
