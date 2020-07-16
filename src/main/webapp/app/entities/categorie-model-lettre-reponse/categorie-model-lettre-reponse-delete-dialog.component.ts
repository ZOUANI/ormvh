import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICategorieModelLettreReponse } from 'app/shared/model/categorie-model-lettre-reponse.model';
import { CategorieModelLettreReponseService } from './categorie-model-lettre-reponse.service';

@Component({
  templateUrl: './categorie-model-lettre-reponse-delete-dialog.component.html',
})
export class CategorieModelLettreReponseDeleteDialogComponent {
  categorieModelLettreReponse?: ICategorieModelLettreReponse;

  constructor(
    protected categorieModelLettreReponseService: CategorieModelLettreReponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.categorieModelLettreReponseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('categorieModelLettreReponseListModification');
      this.activeModal.close();
    });
  }
}
