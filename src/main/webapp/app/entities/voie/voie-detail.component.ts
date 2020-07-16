import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVoie } from 'app/shared/model/voie.model';

@Component({
  selector: 'jhi-voie-detail',
  templateUrl: './voie-detail.component.html',
})
export class VoieDetailComponent implements OnInit {
  voie: IVoie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ voie }) => (this.voie = voie));
  }

  previousState(): void {
    window.history.back();
  }
}
