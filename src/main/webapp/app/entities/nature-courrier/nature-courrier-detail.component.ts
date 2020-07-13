import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureCourrier } from 'app/shared/model/nature-courrier.model';

@Component({
  selector: 'jhi-nature-courrier-detail',
  templateUrl: './nature-courrier-detail.component.html'
})
export class NatureCourrierDetailComponent implements OnInit {
  natureCourrier: INatureCourrier;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ natureCourrier }) => {
      this.natureCourrier = natureCourrier;
    });
  }

  previousState() {
    window.history.back();
  }
}
