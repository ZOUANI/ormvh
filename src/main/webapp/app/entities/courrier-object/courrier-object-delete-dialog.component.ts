import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourrierObject } from 'app/shared/model/courrier-object.model';
import { CourrierObjectService } from './courrier-object.service';

@Component({
  templateUrl: './courrier-object-delete-dialog.component.html',
})
export class CourrierObjectDeleteDialogComponent {
  courrierObject?: ICourrierObject;

  constructor(
    protected courrierObjectService: CourrierObjectService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courrierObjectService.delete(id).subscribe(() => {
      this.eventManager.broadcast('courrierObjectListModification');
      this.activeModal.close();
    });
  }
}
