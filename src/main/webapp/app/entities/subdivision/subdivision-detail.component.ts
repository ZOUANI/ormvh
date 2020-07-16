import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISubdivision } from 'app/shared/model/subdivision.model';

@Component({
  selector: 'jhi-subdivision-detail',
  templateUrl: './subdivision-detail.component.html',
})
export class SubdivisionDetailComponent implements OnInit {
  subdivision: ISubdivision | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ subdivision }) => (this.subdivision = subdivision));
  }

  previousState(): void {
    window.history.back();
  }
}
