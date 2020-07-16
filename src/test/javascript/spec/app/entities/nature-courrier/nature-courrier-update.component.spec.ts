import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { NatureCourrierUpdateComponent } from 'app/entities/nature-courrier/nature-courrier-update.component';
import { NatureCourrierService } from 'app/entities/nature-courrier/nature-courrier.service';
import { NatureCourrier } from 'app/shared/model/nature-courrier.model';

describe('Component Tests', () => {
  describe('NatureCourrier Management Update Component', () => {
    let comp: NatureCourrierUpdateComponent;
    let fixture: ComponentFixture<NatureCourrierUpdateComponent>;
    let service: NatureCourrierService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [NatureCourrierUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(NatureCourrierUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NatureCourrierUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NatureCourrierService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NatureCourrier(123);
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
        const entity = new NatureCourrier();
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
