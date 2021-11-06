import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INBPalette } from '../nb-palette.model';
import { NBPaletteService } from '../service/nb-palette.service';
import { NBPaletteDeleteDialogComponent } from '../delete/nb-palette-delete-dialog.component';

@Component({
  selector: 'jhi-nb-palette',
  templateUrl: './nb-palette.component.html',
})
export class NBPaletteComponent implements OnInit {
  nBPalettes?: INBPalette[];
  isLoading = false;

  constructor(protected nBPaletteService: NBPaletteService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.nBPaletteService.query().subscribe(
      (res: HttpResponse<INBPalette[]>) => {
        this.isLoading = false;
        this.nBPalettes = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: INBPalette): number {
    return item.id!;
  }

  delete(nBPalette: INBPalette): void {
    const modalRef = this.modalService.open(NBPaletteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nBPalette = nBPalette;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
