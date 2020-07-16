import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from './expeditor.service';

@Component({
  templateUrl: './expeditor-delete-dialog.component.html',
})
export class ExpeditorDeleteDialogComponent {
  expeditor?: IExpeditor;

  constructor(protected expeditorService: ExpeditorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expeditorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expeditorListModification');
      this.activeModal.close();
    });
  }
}
