import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { CourrierUpdateComponent } from 'app/entities/courrier/courrier-update.component';
import { CourrierService } from 'app/entities/courrier/courrier.service';
import { Courrier } from 'app/shared/model/courrier.model';

describe('Component Tests', () => {
  describe('Courrier Management Update Component', () => {
    let comp: CourrierUpdateComponent;
    let fixture: ComponentFixture<CourrierUpdateComponent>;
    let service: CourrierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [CourrierUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(CourrierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CourrierUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CourrierService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Courrier(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Courrier();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
