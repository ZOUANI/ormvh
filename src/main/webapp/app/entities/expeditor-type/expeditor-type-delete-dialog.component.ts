import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from './expeditor-type.service';

@Component({
  templateUrl: './expeditor-type-delete-dialog.component.html',
})
export class ExpeditorTypeDeleteDialogComponent {
  expeditorType?: IExpeditorType;

  constructor(
    protected expeditorTypeService: ExpeditorTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.expeditorTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('expeditorTypeListModification');
      this.activeModal.close();
    });
  }
}
