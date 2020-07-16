import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISubdivision } from 'app/shared/model/subdivision.model';
import { SubdivisionService } from './subdivision.service';

@Component({
  templateUrl: './subdivision-delete-dialog.component.html',
})
export class SubdivisionDeleteDialogComponent {
  subdivision?: ISubdivision;

  constructor(
    protected subdivisionService: SubdivisionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.subdivisionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('subdivisionListModification');
      this.activeModal.close();
    });
  }
}
