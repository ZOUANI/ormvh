import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INatureCourrier } from 'app/shared/model/nature-courrier.model';

@Component({
  selector: 'jhi-nature-courrier-detail',
  templateUrl: './nature-courrier-detail.component.html',
})
export class NatureCourrierDetailComponent implements OnInit {
  natureCourrier: INatureCourrier | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ natureCourrier }) => (this.natureCourrier = natureCourrier));
  }

  previousState(): void {
    window.history.back();
  }
}
