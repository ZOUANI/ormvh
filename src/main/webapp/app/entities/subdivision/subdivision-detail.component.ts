import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubdivision } from 'app/shared/model/subdivision.model';

@Component({
  selector: 'jhi-subdivision-detail',
  templateUrl: './subdivision-detail.component.html'
})
export class SubdivisionDetailComponent implements OnInit {
  subdivision: ISubdivision;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ subdivision }) => {
      this.subdivision = subdivision;
    });
  }

  previousState() {
    window.history.back();
  }
}
