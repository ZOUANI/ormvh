import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBordereau } from 'app/shared/model/bordereau.model';
import { BordereauService } from './bordereau.service';

@Component({
  templateUrl: './bordereau-delete-dialog.component.html',
})
export class BordereauDeleteDialogComponent {
  bordereau?: IBordereau;

  constructor(protected bordereauService: BordereauService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.bordereauService.delete(id).subscribe(() => {
      this.eventManager.broadcast('bordereauListModification');
      this.activeModal.close();
    });
  }
}
