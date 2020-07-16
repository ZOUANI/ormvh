import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';

@Component({
  selector: 'jhi-model-lettre-reponse-detail',
  templateUrl: './model-lettre-reponse-detail.component.html',
})
export class ModelLettreReponseDetailComponent implements OnInit {
  modelLettreReponse: IModelLettreReponse | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ modelLettreReponse }) => (this.modelLettreReponse = modelLettreReponse));
  }

  previousState(): void {
    window.history.back();
  }
}
