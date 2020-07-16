import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { NatureCourrierService } from './nature-courrier.service';

@Component({
  templateUrl: './nature-courrier-delete-dialog.component.html',
})
export class NatureCourrierDeleteDialogComponent {
  natureCourrier?: INatureCourrier;

  constructor(
    protected natureCourrierService: NatureCourrierService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.natureCourrierService.delete(id).subscribe(() => {
      this.eventManager.broadcast('natureCourrierListModification');
      this.activeModal.close();
    });
  }
}
