import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INBMap } from '../nb-map.model';

@Component({
  selector: 'jhi-nb-map-detail',
  templateUrl: './nb-map-detail.component.html',
})
export class NBMapDetailComponent implements OnInit {
  nBMap: INBMap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBMap }) => {
      this.nBMap = nBMap;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
