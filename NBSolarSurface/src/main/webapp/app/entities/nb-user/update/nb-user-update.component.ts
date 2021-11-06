import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INBUser, NBUser } from '../nb-user.model';
import { NBUserService } from '../service/nb-user.service';

@Component({
  selector: 'jhi-nb-user-update',
  templateUrl: './nb-user-update.component.html',
})
export class NBUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nbUserID: [],
    nbAuthType: [],
    nbPasswordHash: [],
    nbFirstName: [],
    nbLastName: [],
    nbAddress: [],
    nbEmailId: [],
    nbPhone: [],
    nbIsActive: [],
    nbIsSuspended: [],
    nbIsBanished: [],
    nbLastUpdated: [],
    nbLastUpdatedBy: [],
  });

  constructor(protected nBUserService: NBUserService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBUser }) => {
      this.updateForm(nBUser);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nBUser = this.createFromForm();
    if (nBUser.id !== undefined) {
      this.subscribeToSaveResponse(this.nBUserService.update(nBUser));
    } else {
      this.subscribeToSaveResponse(this.nBUserService.create(nBUser));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INBUser>>): void {
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

  protected updateForm(nBUser: INBUser): void {
    this.editForm.patchValue({
      id: nBUser.id,
      nbUserID: nBUser.nbUserID,
      nbAuthType: nBUser.nbAuthType,
      nbPasswordHash: nBUser.nbPasswordHash,
      nbFirstName: nBUser.nbFirstName,
      nbLastName: nBUser.nbLastName,
      nbAddress: nBUser.nbAddress,
      nbEmailId: nBUser.nbEmailId,
      nbPhone: nBUser.nbPhone,
      nbIsActive: nBUser.nbIsActive,
      nbIsSuspended: nBUser.nbIsSuspended,
      nbIsBanished: nBUser.nbIsBanished,
      nbLastUpdated: nBUser.nbLastUpdated,
      nbLastUpdatedBy: nBUser.nbLastUpdatedBy,
    });
  }

  protected createFromForm(): INBUser {
    return {
      ...new NBUser(),
      id: this.editForm.get(['id'])!.value,
      nbUserID: this.editForm.get(['nbUserID'])!.value,
      nbAuthType: this.editForm.get(['nbAuthType'])!.value,
      nbPasswordHash: this.editForm.get(['nbPasswordHash'])!.value,
      nbFirstName: this.editForm.get(['nbFirstName'])!.value,
      nbLastName: this.editForm.get(['nbLastName'])!.value,
      nbAddress: this.editForm.get(['nbAddress'])!.value,
      nbEmailId: this.editForm.get(['nbEmailId'])!.value,
      nbPhone: this.editForm.get(['nbPhone'])!.value,
      nbIsActive: this.editForm.get(['nbIsActive'])!.value,
      nbIsSuspended: this.editForm.get(['nbIsSuspended'])!.value,
      nbIsBanished: this.editForm.get(['nbIsBanished'])!.value,
      nbLastUpdated: this.editForm.get(['nbLastUpdated'])!.value,
      nbLastUpdatedBy: this.editForm.get(['nbLastUpdatedBy'])!.value,
    };
  }
}
