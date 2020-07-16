import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IModelLettreReponse } from 'app/shared/model/model-lettre-reponse.model';
import { ModelLettreReponseService } from './model-lettre-reponse.service';

@Component({
  templateUrl: './model-lettre-reponse-delete-dialog.component.html',
})
export class ModelLettreReponseDeleteDialogComponent {
  modelLettreReponse?: IModelLettreReponse;

  constructor(
    protected modelLettreReponseService: ModelLettreReponseService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.modelLettreReponseService.delete(id).subscribe(() => {
      this.eventManager.broadcast('modelLettreReponseListModification');
      this.activeModal.close();
    });
  }
}
