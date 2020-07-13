import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';

@Component({
  selector: 'jhi-categorie-model-lettre-reponse-detail',
  templateUrl: './categorie-model-lettre-reponse-detail.component.html'
})
export class CategorieModelLettreReponseDetailComponent implements OnInit {
  categorieModelLettreReponse: ICategorieModelLettreReponse;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ categorieModelLettreReponse }) => {
      this.categorieModelLettreReponse = categorieModelLettreReponse;
    });
  }

  previousState() {
    window.history.back();
  }
}
