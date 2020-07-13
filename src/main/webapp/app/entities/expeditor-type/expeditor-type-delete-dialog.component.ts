import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IExpeditorType } from 'app/shared/model/expeditor-type.model';
import { ExpeditorTypeService } from './expeditor-type.service';

@Component({
  selector: 'jhi-expeditor-type-delete-dialog',
  templateUrl: './expeditor-type-delete-dialog.component.html'
})
export class ExpeditorTypeDeleteDialogComponent {
  expeditorType: IExpeditorType;

  constructor(
    protected expeditorTypeService: ExpeditorTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.expeditorTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'expeditorTypeListModification',
        content: 'Deleted an expeditorType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-expeditor-type-delete-popup',
  template: ''
})
export class ExpeditorTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ expeditorType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ExpeditorTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.expeditorType = expeditorType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/expeditor-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/expeditor-type', { outlets: { popup: null } }]);
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
