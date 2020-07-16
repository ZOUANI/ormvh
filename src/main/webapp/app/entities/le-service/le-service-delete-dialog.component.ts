import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILeService } from 'app/shared/model/le-service.model';
import { LeServiceService } from './le-service.service';

@Component({
  templateUrl: './le-service-delete-dialog.component.html',
})
export class LeServiceDeleteDialogComponent {
  leService?: ILeService;

  constructor(protected leServiceService: LeServiceService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.leServiceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('leServiceListModification');
      this.activeModal.close();
    });
  }
}
