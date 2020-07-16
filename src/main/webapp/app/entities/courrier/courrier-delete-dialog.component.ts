import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICourrier } from 'app/shared/model/courrier.model';
import { CourrierService } from './courrier.service';

@Component({
  templateUrl: './courrier-delete-dialog.component.html',
})
export class CourrierDeleteDialogComponent {
  courrier?: ICourrier;

  constructor(protected courrierService: CourrierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.courrierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('courrierListModification');
      this.activeModal.close();
    });
  }
}
