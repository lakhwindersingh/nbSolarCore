import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INBUser } from '../nb-user.model';
import { NBUserService } from '../service/nb-user.service';
import { NBUserDeleteDialogComponent } from '../delete/nb-user-delete-dialog.component';

@Component({
  selector: 'jhi-nb-user',
  templateUrl: './nb-user.component.html',
})
export class NBUserComponent implements OnInit {
  nBUsers?: INBUser[];
  isLoading = false;

  constructor(protected nBUserService: NBUserService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nBUserService.query().subscribe(
      (res: HttpResponse<INBUser[]>) => {
        this.isLoading = false;
        this.nBUsers = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INBUser): number {
    return item.id!;
  }

  delete(nBUser: INBUser): void {
    const modalRef = this.modalService.open(NBUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nBUser = nBUser;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
