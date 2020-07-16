import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVoie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';

@Component({
  templateUrl: './voie-delete-dialog.component.html',
})
export class VoieDeleteDialogComponent {
  voie?: IVoie;

  constructor(protected voieService: VoieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.voieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('voieListModification');
      this.activeModal.close();
    });
  }
}
