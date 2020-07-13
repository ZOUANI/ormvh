import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICourrierObject } from 'app/shared/model/courrier-object.model';

@Component({
  selector: 'jhi-courrier-object-detail',
  templateUrl: './courrier-object-detail.component.html'
})
export class CourrierObjectDetailComponent implements OnInit {
  courrierObject: ICourrierObject;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ courrierObject }) => {
      this.courrierObject = courrierObject;
    });
  }

  previousState() {
    window.history.back();
  }
}
