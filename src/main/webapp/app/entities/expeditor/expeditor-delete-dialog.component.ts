import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpeditor } from 'app/shared/model/expeditor.model';
import { ExpeditorService } from './expeditor.service';

@Component({
  selector: 'jhi-expeditor-delete-dialog',
  templateUrl: './expeditor-delete-dialog.component.html'
})
export class ExpeditorDeleteDialogComponent {
  expeditor: IExpeditor;

  constructor(protected expeditorService: ExpeditorService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.expeditorService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'expeditorListModification',
        content: 'Deleted an expeditor'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-expeditor-delete-popup',
  template: ''
})
export class ExpeditorDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ expeditor }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ExpeditorDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.expeditor = expeditor;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/expeditor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/expeditor', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
