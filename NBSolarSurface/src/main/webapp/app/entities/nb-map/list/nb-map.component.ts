import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INBMap } from '../nb-map.model';
import { NBMapService } from '../service/nb-map.service';
import { NBMapDeleteDialogComponent } from '../delete/nb-map-delete-dialog.component';

@Component({
  selector: 'jhi-nb-map',
  templateUrl: './nb-map.component.html',
})
export class NBMapComponent implements OnInit {
  nBMaps?: INBMap[];
  isLoading = false;

  constructor(protected nBMapService: NBMapService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nBMapService.query().subscribe(
      (res: HttpResponse<INBMap[]>) => {
        this.isLoading = false;
        this.nBMaps = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INBMap): number {
    return item.id!;
  }

  delete(nBMap: INBMap): void {
    const modalRef = this.modalService.open(NBMapDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nBMap = nBMap;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
