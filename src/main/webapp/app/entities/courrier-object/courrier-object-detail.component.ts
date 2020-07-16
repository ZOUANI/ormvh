import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourrierObject } from 'app/shared/model/courrier-object.model';

@Component({
  selector: 'jhi-courrier-object-detail',
  templateUrl: './courrier-object-detail.component.html',
})
export class CourrierObjectDetailComponent implements OnInit {
  courrierObject: ICourrierObject | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ courrierObject }) => (this.courrierObject = courrierObject));
  }

  previousState(): void {
    window.history.back();
  }
}
