import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INBUser } from '../nb-user.model';

@Component({
  selector: 'jhi-nb-user-detail',
  templateUrl: './nb-user-detail.component.html',
})
export class NBUserDetailComponent implements OnInit {
  nBUser: INBUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nBUser }) => {
      this.nBUser = nBUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
