import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INBPalette } from '../nb-palette.model';

@Component({
  selector: 'jhi-nb-palette-detail',
  templateUrl: './nb-palette-detail.component.html',
})
export class NBPaletteDetailComponent implements OnInit {
  nBPalette: INBPalette | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBPalette }) => {
      this.nBPalette = nBPalette;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
